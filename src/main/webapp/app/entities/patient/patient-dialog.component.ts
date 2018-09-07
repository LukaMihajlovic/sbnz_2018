import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Patient } from './patient.model';
import { PatientPopupService } from './patient-popup.service';
import { PatientService } from './patient.service';

@Component({
    selector: 'jhi-patient-dialog',
    templateUrl: './patient-dialog.component.html'
})
export class PatientDialogComponent implements OnInit {

    patient: Patient;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private patientService: PatientService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.patient.id !== undefined) {
            this.subscribeToSaveResponse(
                this.patientService.update(this.patient));
        } else {
            this.subscribeToSaveResponse(
                this.patientService.create(this.patient));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Patient>>) {
        result.subscribe((res: HttpResponse<Patient>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Patient) {
        this.eventManager.broadcast({ name: 'patientListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-patient-popup',
    template: ''
})
export class PatientPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private patientPopupService: PatientPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.patientPopupService
                    .open(PatientDialogComponent as Component, params['id']);
            } else {
                this.patientPopupService
                    .open(PatientDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
