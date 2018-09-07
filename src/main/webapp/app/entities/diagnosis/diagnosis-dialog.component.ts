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

@Component({
    selector: 'jhi-diagnosis-dialog',
    templateUrl: './diagnosis-dialog.component.html'
})
export class DiagnosisDialogComponent implements OnInit {

    diagnosis: Diagnosis;
    isSaving: boolean;

    doctors: Doctor[];
    dateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private diagnosisService: DiagnosisService,
        private doctorService: DoctorService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.doctorService.query()
            .subscribe((res: HttpResponse<Doctor[]>) => { this.doctors = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
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
