import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Disease } from './disease.model';
import { DiseasePopupService } from './disease-popup.service';
import { DiseaseService } from './disease.service';
import { Symptom, SymptomService } from '../symptom';
import { Diagnosis, DiagnosisService } from '../diagnosis';

@Component({
    selector: 'jhi-disease-dialog',
    templateUrl: './disease-dialog.component.html'
})
export class DiseaseDialogComponent implements OnInit {

    disease: Disease;
    isSaving: boolean;

    symptoms: Symptom[];

    diagnoses: Diagnosis[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private diseaseService: DiseaseService,
        private symptomService: SymptomService,
        private diagnosisService: DiagnosisService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.symptomService.query()
            .subscribe((res: HttpResponse<Symptom[]>) => { this.symptoms = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.diagnosisService.query()
            .subscribe((res: HttpResponse<Diagnosis[]>) => { this.diagnoses = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.disease.id !== undefined) {
            this.subscribeToSaveResponse(
                this.diseaseService.update(this.disease));
        } else {
            this.subscribeToSaveResponse(
                this.diseaseService.create(this.disease));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Disease>>) {
        result.subscribe((res: HttpResponse<Disease>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Disease) {
        this.eventManager.broadcast({ name: 'diseaseListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackSymptomById(index: number, item: Symptom) {
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
    selector: 'jhi-disease-popup',
    template: ''
})
export class DiseasePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private diseasePopupService: DiseasePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.diseasePopupService
                    .open(DiseaseDialogComponent as Component, params['id']);
            } else {
                this.diseasePopupService
                    .open(DiseaseDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
