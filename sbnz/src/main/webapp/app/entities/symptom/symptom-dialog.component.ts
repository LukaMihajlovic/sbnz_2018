import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Symptom } from './symptom.model';
import { SymptomPopupService } from './symptom-popup.service';
import { SymptomService } from './symptom.service';
import { Diagnosis, DiagnosisService } from '../diagnosis';
import { Disease, DiseaseService } from '../disease';

@Component({
    selector: 'jhi-symptom-dialog',
    templateUrl: './symptom-dialog.component.html'
})
export class SymptomDialogComponent implements OnInit {

    symptom: Symptom;
    isSaving: boolean;

    diagnoses: Diagnosis[];

    diseases: Disease[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private symptomService: SymptomService,
        private diagnosisService: DiagnosisService,
        private diseaseService: DiseaseService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.diagnosisService.query()
            .subscribe((res: HttpResponse<Diagnosis[]>) => { this.diagnoses = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.diseaseService.query()
            .subscribe((res: HttpResponse<Disease[]>) => { this.diseases = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.symptom.id !== undefined) {
            this.subscribeToSaveResponse(
                this.symptomService.update(this.symptom));
        } else {
            this.subscribeToSaveResponse(
                this.symptomService.create(this.symptom));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Symptom>>) {
        result.subscribe((res: HttpResponse<Symptom>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Symptom) {
        this.eventManager.broadcast({ name: 'symptomListModification', content: 'OK'});
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

    trackDiseaseById(index: number, item: Disease) {
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
    selector: 'jhi-symptom-popup',
    template: ''
})
export class SymptomPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private symptomPopupService: SymptomPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.symptomPopupService
                    .open(SymptomDialogComponent as Component, params['id']);
            } else {
                this.symptomPopupService
                    .open(SymptomDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
