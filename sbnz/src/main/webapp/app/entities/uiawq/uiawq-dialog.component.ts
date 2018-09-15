import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Uiawq } from './uiawq.model';
import { UiawqPopupService } from './uiawq-popup.service';
import { UiawqService } from './uiawq.service';

@Component({
    selector: 'jhi-uiawq-dialog',
    templateUrl: './uiawq-dialog.component.html'
})
export class UiawqDialogComponent implements OnInit {

    uiawq: Uiawq;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private uiawqService: UiawqService,
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
        if (this.uiawq.id !== undefined) {
            this.subscribeToSaveResponse(
                this.uiawqService.update(this.uiawq));
        } else {
            this.subscribeToSaveResponse(
                this.uiawqService.create(this.uiawq));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Uiawq>>) {
        result.subscribe((res: HttpResponse<Uiawq>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Uiawq) {
        this.eventManager.broadcast({ name: 'uiawqListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-uiawq-popup',
    template: ''
})
export class UiawqPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private uiawqPopupService: UiawqPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.uiawqPopupService
                    .open(UiawqDialogComponent as Component, params['id']);
            } else {
                this.uiawqPopupService
                    .open(UiawqDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
