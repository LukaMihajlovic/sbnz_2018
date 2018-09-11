import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Diagnosis } from './diagnosis.model';
import { DiagnosisPopupService } from './diagnosis-popup.service';
import { DiagnosisService } from './diagnosis.service';
import { Doctor, DoctorService } from '../doctor';
import { Anamnesis, AnamnesisService } from '../anamnesis';
import { Drug, DrugService } from '../drug';
import { Symptom, SymptomService } from '../symptom';

@Component({
    selector: 'jhi-diagnosis-dialog',
    templateUrl: './diagnosis-dialog.component.html'
})
export class DiagnosisDialogComponent implements OnInit {

    diagnosis: Diagnosis;
    isSaving: boolean;

    doctors: Doctor[];

    anamneses: Anamnesis[];

    drugs: Drug[];

    symptoms: Symptom[];
    dateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private diagnosisService: DiagnosisService,
        private doctorService: DoctorService,
        private anamnesisService: AnamnesisService,
        private drugService: DrugService,
        private symptomService: SymptomService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.doctorService.query()
            .subscribe((res: HttpResponse<Doctor[]>) => { this.doctors = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.anamnesisService.query()
            .subscribe((res: HttpResponse<Anamnesis[]>) => { this.anamneses = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.drugService.query()
            .subscribe((res: HttpResponse<Drug[]>) => { this.drugs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.symptomService.query()
            .subscribe((res: HttpResponse<Symptom[]>) => { this.symptoms = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.diagnosis.id !== undefined) {
            this.subscribeToSaveResponse(
                this.diagnosisService.update(this.diagnosis));
        } else {
            this.subscribeToSaveResponse(
                this.diagnosisService.create(this.diagnosis));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Diagnosis>>) {
        result.subscribe((res: HttpResponse<Diagnosis>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Diagnosis) {
        this.eventManager.broadcast({ name: 'diagnosisListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackDoctorById(index: number, item: Doctor) {
        return item.id;
    }

    trackAnamnesisById(index: number, item: Anamnesis) {
        return item.id;
    }

    trackDrugById(index: number, item: Drug) {
        return item.id;
    }

    trackSymptomById(index: number, item: Symptom) {
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
    selector: 'jhi-diagnosis-popup',
    template: ''
})
export class DiagnosisPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private diagnosisPopupService: DiagnosisPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.diagnosisPopupService
                    .open(DiagnosisDialogComponent as Component, params['id']);
            } else {
                this.diagnosisPopupService
                    .open(DiagnosisDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
