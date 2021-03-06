import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Drug } from './drug.model';
import { DrugPopupService } from './drug-popup.service';
import { DrugService } from './drug.service';
import { Ingredient, IngredientService } from '../ingredient';
import { Anamnesis, AnamnesisService } from '../anamnesis';
import { Diagnosis, DiagnosisService } from '../diagnosis';

@Component({
    selector: 'jhi-drug-dialog',
    templateUrl: './drug-dialog.component.html'
})
export class DrugDialogComponent implements OnInit {

    drug: Drug;
    isSaving: boolean;

    ingredients: Ingredient[];

    anamneses: Anamnesis[];

    diagnoses: Diagnosis[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private drugService: DrugService,
        private ingredientService: IngredientService,
        private anamnesisService: AnamnesisService,
        private diagnosisService: DiagnosisService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.ingredientService.query()
            .subscribe((res: HttpResponse<Ingredient[]>) => { this.ingredients = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.anamnesisService.query()
            .subscribe((res: HttpResponse<Anamnesis[]>) => { this.anamneses = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.diagnosisService.query()
            .subscribe((res: HttpResponse<Diagnosis[]>) => { this.diagnoses = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.drug.id !== undefined) {
            this.subscribeToSaveResponse(
                this.drugService.update(this.drug));
        } else {
            this.subscribeToSaveResponse(
                this.drugService.create(this.drug));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Drug>>) {
        result.subscribe((res: HttpResponse<Drug>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Drug) {
        this.eventManager.broadcast({ name: 'drugListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackIngredientById(index: number, item: Ingredient) {
        return item.id;
    }

    trackAnamnesisById(index: number, item: Anamnesis) {
        return item.id;
    }

    trackDiagnosisById(index: number, item: Diagnosis) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-drug-popup',
    template: ''
})
export class DrugPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private drugPopupService: DrugPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.drugPopupService
                    .open(DrugDialogComponent as Component, params['id']);
            } else {
                this.drugPopupService
                    .open(DrugDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
