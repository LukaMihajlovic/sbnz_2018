import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { SymptomComponent } from './symptom.component';
import { SymptomDetailComponent } from './symptom-detail.component';
import { SymptomPopupComponent } from './symptom-dialog.component';
import { SymptomDeletePopupComponent } from './symptom-delete-dialog.component';

export const symptomRoute: Routes = [
    {
        path: 'symptom',
        component: SymptomComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Symptoms'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'symptom/:id',
        component: SymptomDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Symptoms'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const symptomPopupRoute: Routes = [
    {
        path: 'symptom-new',
        component: SymptomPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Symptoms'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'symptom/:id/edit',
        component: SymptomPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Symptoms'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'symptom/:id/delete',
        component: SymptomDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Symptoms'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
