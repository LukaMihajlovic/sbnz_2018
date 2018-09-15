import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Ingredient } from './ingredient.model';
import { IngredientService } from './ingredient.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-ingredient',
    templateUrl: './ingredient.component.html'
})
export class IngredientComponent implements OnInit, OnDestroy {
ingredients: Ingredient[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private ingredientService: IngredientService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.ingredientService.query().subscribe(
            (res: HttpResponse<Ingredient[]>) => {
                this.ingredients = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInIngredients();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Ingredient) {
        return item.id;
    }
    registerChangeInIngredients() {
        this.eventSubscriber = this.eventManager.subscribe('ingredientListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
