import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Diagnosis } from './diagnosis.model';
import { DiagnosisService } from './diagnosis.service';

@Component({
    selector: 'jhi-diagnosis-detail',
    templateUrl: './diagnosis-detail.component.html'
})
export class DiagnosisDetailComponent implements OnInit, OnDestroy {

    diagnosis: Diagnosis;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private diagnosisService: DiagnosisService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDiagnoses();
    }

    load(id) {
        this.diagnosisService.find(id)
            .subscribe((diagnosisResponse: HttpResponse<Diagnosis>) => {
                this.diagnosis = diagnosisResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDiagnoses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'diagnosisListModification',
            (response) => this.load(this.diagnosis.id)
        );
    }
}
