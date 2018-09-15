import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Drug } from './drug.model';
import { DrugPopupService } from './drug-popup.service';
import { DrugService } from './drug.service';

@Component({
    selector: 'jhi-drug-delete-dialog',
    templateUrl: './drug-delete-dialog.component.html'
})
export class DrugDeleteDialogComponent {

    drug: Drug;

    constructor(
        private drugService: DrugService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.drugService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'drugListModification',
                content: 'Deleted an drug'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-drug-delete-popup',
    template: ''
})
export class DrugDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private drugPopupService: DrugPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.drugPopupService
                .open(DrugDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
