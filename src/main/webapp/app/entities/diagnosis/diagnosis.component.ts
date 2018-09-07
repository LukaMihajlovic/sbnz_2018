import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Diagnosis } from './diagnosis.model';
import { DiagnosisService } from './diagnosis.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-diagnosis',
    templateUrl: './diagnosis.component.html'
})
export class DiagnosisComponent implements OnInit, OnDestroy {
diagnoses: Diagnosis[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private diagnosisService: DiagnosisService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.diagnosisService.query().subscribe(
            (res: HttpResponse<Diagnosis[]>) => {
                this.diagnoses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDiagnoses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Diagnosis) {
        return item.id;
    }
    registerChangeInDiagnoses() {
        this.eventSubscriber = this.eventManager.subscribe('diagnosisListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
