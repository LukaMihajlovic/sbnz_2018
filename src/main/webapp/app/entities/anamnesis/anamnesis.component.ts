import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Anamnesis } from './anamnesis.model';
import { AnamnesisService } from './anamnesis.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-anamnesis',
    templateUrl: './anamnesis.component.html'
})
export class AnamnesisComponent implements OnInit, OnDestroy {
anamneses: Anamnesis[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private anamnesisService: AnamnesisService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.anamnesisService.query().subscribe(
            (res: HttpResponse<Anamnesis[]>) => {
                this.anamneses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAnamneses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Anamnesis) {
        return item.id;
    }
    registerChangeInAnamneses() {
        this.eventSubscriber = this.eventManager.subscribe('anamnesisListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
