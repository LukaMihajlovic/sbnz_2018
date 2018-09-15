import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Anamnesis } from './anamnesis.model';
import { AnamnesisPopupService } from './anamnesis-popup.service';
import { AnamnesisService } from './anamnesis.service';
import { Diagnosis, DiagnosisService } from '../diagnosis';
import { Ingredient, IngredientService } from '../ingredient';
import { Drug, DrugService } from '../drug';

@Component({
    selector: 'jhi-anamnesis-dialog',
    templateUrl: './anamnesis-dialog.component.html'
})
export class AnamnesisDialogComponent implements OnInit {

    anamnesis: Anamnesis;
    isSaving: boolean;

    currentdiagnoses: Diagnosis[];

    ingredients: Ingredient[];

    drugs: Drug[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private anamnesisService: AnamnesisService,
        private diagnosisService: DiagnosisService,
        private ingredientService: IngredientService,
        private drugService: DrugService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.diagnosisService
            .query({filter: 'anamnesis-is-null'})
            .subscribe((res: HttpResponse<Diagnosis[]>) => {
                if (!this.anamnesis.currentDiagnosis || !this.anamnesis.currentDiagnosis.id) {
                    this.currentdiagnoses = res.body;
                } else {
                    this.diagnosisService
                        .find(this.anamnesis.currentDiagnosis.id)
                        .subscribe((subRes: HttpResponse<Diagnosis>) => {
                            this.currentdiagnoses = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.ingredientService.query()
            .subscribe((res: HttpResponse<Ingredient[]>) => { this.ingredients = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.drugService.query()
            .subscribe((res: HttpResponse<Drug[]>) => { this.drugs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.anamnesis.id !== undefined) {
            this.subscribeToSaveResponse(
                this.anamnesisService.update(this.anamnesis));
        } else {
            this.subscribeToSaveResponse(
                this.anamnesisService.create(this.anamnesis));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Anamnesis>>) {
        result.subscribe((res: HttpResponse<Anamnesis>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Anamnesis) {
        this.eventManager.broadcast({ name: 'anamnesisListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackDiagnosisById(index: number, item: Diagnosis) {
        return item.id;
    }

    trackIngredientById(index: number, item: Ingredient) {
        return item.id;
    }

    trackDrugById(index: number, item: Drug) {
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
    selector: 'jhi-anamnesis-popup',
    template: ''
})
export class AnamnesisPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private anamnesisPopupService: AnamnesisPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.anamnesisPopupService
                    .open(AnamnesisDialogComponent as Component, params['id']);
            } else {
                this.anamnesisPopupService
                    .open(AnamnesisDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
