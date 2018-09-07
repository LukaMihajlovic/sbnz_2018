import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Drug } from './drug.model';
import { DrugService } from './drug.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-drug',
    templateUrl: './drug.component.html'
})
export class DrugComponent implements OnInit, OnDestroy {
drugs: Drug[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private drugService: DrugService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.drugService.query().subscribe(
            (res: HttpResponse<Drug[]>) => {
                this.drugs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDrugs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Drug) {
        return item.id;
    }
    registerChangeInDrugs() {
        this.eventSubscriber = this.eventManager.subscribe('drugListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
