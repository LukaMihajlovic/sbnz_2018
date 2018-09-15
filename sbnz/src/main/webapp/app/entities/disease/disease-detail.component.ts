import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Disease } from './disease.model';
import { DiseaseService } from './disease.service';

@Component({
    selector: 'jhi-disease-detail',
    templateUrl: './disease-detail.component.html'
})
export class DiseaseDetailComponent implements OnInit, OnDestroy {

    disease: Disease;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private diseaseService: DiseaseService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDiseases();
    }

    load(id) {
        this.diseaseService.find(id)
            .subscribe((diseaseResponse: HttpResponse<Disease>) => {
                this.disease = diseaseResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDiseases() {
        this.eventSubscriber = this.eventManager.subscribe(
            'diseaseListModification',
            (response) => this.load(this.disease.id)
        );
    }
}
