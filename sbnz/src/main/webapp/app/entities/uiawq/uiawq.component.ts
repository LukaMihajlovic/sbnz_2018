import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Uiawq } from './uiawq.model';
import { UiawqService } from './uiawq.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-uiawq',
    templateUrl: './uiawq.component.html'
})
export class UiawqComponent implements OnInit, OnDestroy {
uiawqs: Uiawq[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private uiawqService: UiawqService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.uiawqService.query().subscribe(
            (res: HttpResponse<Uiawq[]>) => {
                this.uiawqs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInUiawqs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Uiawq) {
        return item.id;
    }
    registerChangeInUiawqs() {
        this.eventSubscriber = this.eventManager.subscribe('uiawqListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
