import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Symptom } from './symptom.model';
import { SymptomPopupService } from './symptom-popup.service';
import { SymptomService } from './symptom.service';

@Component({
    selector: 'jhi-symptom-delete-dialog',
    templateUrl: './symptom-delete-dialog.component.html'
})
export class SymptomDeleteDialogComponent {

    symptom: Symptom;

    constructor(
        private symptomService: SymptomService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.symptomService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'symptomListModification',
                content: 'Deleted an symptom'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-symptom-delete-popup',
    template: ''
})
export class SymptomDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private symptomPopupService: SymptomPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.symptomPopupService
                .open(SymptomDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
