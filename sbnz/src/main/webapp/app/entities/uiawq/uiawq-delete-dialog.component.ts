import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Uiawq } from './uiawq.model';
import { UiawqPopupService } from './uiawq-popup.service';
import { UiawqService } from './uiawq.service';

@Component({
    selector: 'jhi-uiawq-delete-dialog',
    templateUrl: './uiawq-delete-dialog.component.html'
})
export class UiawqDeleteDialogComponent {

    uiawq: Uiawq;

    constructor(
        private uiawqService: UiawqService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.uiawqService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'uiawqListModification',
                content: 'Deleted an uiawq'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-uiawq-delete-popup',
    template: ''
})
export class UiawqDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private uiawqPopupService: UiawqPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.uiawqPopupService
                .open(UiawqDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
