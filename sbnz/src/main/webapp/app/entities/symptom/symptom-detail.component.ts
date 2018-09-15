import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Symptom } from './symptom.model';
import { SymptomService } from './symptom.service';

@Component({
    selector: 'jhi-symptom-detail',
    templateUrl: './symptom-detail.component.html'
})
export class SymptomDetailComponent implements OnInit, OnDestroy {

    symptom: Symptom;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private symptomService: SymptomService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSymptoms();
    }

    load(id) {
        this.symptomService.find(id)
            .subscribe((symptomResponse: HttpResponse<Symptom>) => {
                this.symptom = symptomResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSymptoms() {
        this.eventSubscriber = this.eventManager.subscribe(
            'symptomListModification',
            (response) => this.load(this.symptom.id)
        );
    }
}
