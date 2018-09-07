import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Drug } from './drug.model';
import { DrugService } from './drug.service';

@Component({
    selector: 'jhi-drug-detail',
    templateUrl: './drug-detail.component.html'
})
export class DrugDetailComponent implements OnInit, OnDestroy {

    drug: Drug;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private drugService: DrugService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDrugs();
    }

    load(id) {
        this.drugService.find(id)
            .subscribe((drugResponse: HttpResponse<Drug>) => {
                this.drug = drugResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDrugs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'drugListModification',
            (response) => this.load(this.drug.id)
        );
    }
}
