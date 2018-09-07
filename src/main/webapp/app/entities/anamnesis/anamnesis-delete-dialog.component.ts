import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Anamnesis } from './anamnesis.model';
import { AnamnesisPopupService } from './anamnesis-popup.service';
import { AnamnesisService } from './anamnesis.service';

@Component({
    selector: 'jhi-anamnesis-delete-dialog',
    templateUrl: './anamnesis-delete-dialog.component.html'
})
export class AnamnesisDeleteDialogComponent {

    anamnesis: Anamnesis;

    constructor(
        private anamnesisService: AnamnesisService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.anamnesisService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'anamnesisListModification',
                content: 'Deleted an anamnesis'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-anamnesis-delete-popup',
    template: ''
})
export class AnamnesisDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private anamnesisPopupService: AnamnesisPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.anamnesisPopupService
                .open(AnamnesisDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
