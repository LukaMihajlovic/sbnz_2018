import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Symptom } from './symptom.model';
import { SymptomService } from './symptom.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-symptom',
    templateUrl: './symptom.component.html'
})
export class SymptomComponent implements OnInit, OnDestroy {
symptoms: Symptom[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private symptomService: SymptomService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.symptomService.query().subscribe(
            (res: HttpResponse<Symptom[]>) => {
                this.symptoms = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSymptoms();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Symptom) {
        return item.id;
    }
    registerChangeInSymptoms() {
        this.eventSubscriber = this.eventManager.subscribe('symptomListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
