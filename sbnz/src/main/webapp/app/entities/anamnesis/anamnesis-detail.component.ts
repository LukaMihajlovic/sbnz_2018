import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Anamnesis } from './anamnesis.model';
import { AnamnesisService } from './anamnesis.service';

@Component({
    selector: 'jhi-anamnesis-detail',
    templateUrl: './anamnesis-detail.component.html'
})
export class AnamnesisDetailComponent implements OnInit, OnDestroy {

    anamnesis: Anamnesis;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private anamnesisService: AnamnesisService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAnamneses();
    }

    load(id) {
        this.anamnesisService.find(id)
            .subscribe((anamnesisResponse: HttpResponse<Anamnesis>) => {
                this.anamnesis = anamnesisResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAnamneses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'anamnesisListModification',
            (response) => this.load(this.anamnesis.id)
        );
    }
}
