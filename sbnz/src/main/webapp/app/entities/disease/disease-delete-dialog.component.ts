import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Disease } from './disease.model';
import { DiseasePopupService } from './disease-popup.service';
import { DiseaseService } from './disease.service';

@Component({
    selector: 'jhi-disease-delete-dialog',
    templateUrl: './disease-delete-dialog.component.html'
})
export class DiseaseDeleteDialogComponent {

    disease: Disease;

    constructor(
        private diseaseService: DiseaseService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.diseaseService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'diseaseListModification',
                content: 'Deleted an disease'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-disease-delete-popup',
    template: ''
})
export class DiseaseDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private diseasePopupService: DiseasePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.diseasePopupService
                .open(DiseaseDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
