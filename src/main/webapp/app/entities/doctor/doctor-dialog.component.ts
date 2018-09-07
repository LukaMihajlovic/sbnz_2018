import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Doctor } from './doctor.model';
import { DoctorPopupService } from './doctor-popup.service';
import { DoctorService } from './doctor.service';
import { User, UserService } from '../../shared';

@Component({
    selector: 'jhi-doctor-dialog',
    templateUrl: './doctor-dialog.component.html'
})
export class DoctorDialogComponent implements OnInit {

    doctor: Doctor;
    isSaving: boolean;

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private doctorService: DoctorService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.doctor.id !== undefined) {
            this.subscribeToSaveResponse(
                this.doctorService.update(this.doctor));
        } else {
            this.subscribeToSaveResponse(
                this.doctorService.create(this.doctor));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Doctor>>) {
        result.subscribe((res: HttpResponse<Doctor>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Doctor) {
        this.eventManager.broadcast({ name: 'doctorListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-doctor-popup',
    template: ''
})
export class DoctorPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private doctorPopupService: DoctorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.doctorPopupService
                    .open(DoctorDialogComponent as Component, params['id']);
            } else {
                this.doctorPopupService
                    .open(DoctorDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
