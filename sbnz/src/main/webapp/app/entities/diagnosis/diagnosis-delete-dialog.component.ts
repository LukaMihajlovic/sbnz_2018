import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Diagnosis } from './diagnosis.model';
import { DiagnosisPopupService } from './diagnosis-popup.service';
import { DiagnosisService } from './diagnosis.service';

@Component({
    selector: 'jhi-diagnosis-delete-dialog',
    templateUrl: './diagnosis-delete-dialog.component.html'
})
export class DiagnosisDeleteDialogComponent {

    diagnosis: Diagnosis;

    constructor(
        private diagnosisService: DiagnosisService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.diagnosisService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'diagnosisListModification',
                content: 'Deleted an diagnosis'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-diagnosis-delete-popup',
    template: ''
})
export class DiagnosisDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private diagnosisPopupService: DiagnosisPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.diagnosisPopupService
                .open(DiagnosisDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
