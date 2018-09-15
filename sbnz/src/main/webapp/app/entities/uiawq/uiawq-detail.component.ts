import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Uiawq } from './uiawq.model';
import { UiawqService } from './uiawq.service';

@Component({
    selector: 'jhi-uiawq-detail',
    templateUrl: './uiawq-detail.component.html'
})
export class UiawqDetailComponent implements OnInit, OnDestroy {

    uiawq: Uiawq;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private uiawqService: UiawqService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUiawqs();
    }

    load(id) {
        this.uiawqService.find(id)
            .subscribe((uiawqResponse: HttpResponse<Uiawq>) => {
                this.uiawq = uiawqResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUiawqs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'uiawqListModification',
            (response) => this.load(this.uiawq.id)
        );
    }
}
