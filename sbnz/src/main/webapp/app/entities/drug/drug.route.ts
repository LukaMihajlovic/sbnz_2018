import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DrugComponent } from './drug.component';
import { DrugDetailComponent } from './drug-detail.component';
import { DrugPopupComponent } from './drug-dialog.component';
import { DrugDeletePopupComponent } from './drug-delete-dialog.component';

export const drugRoute: Routes = [
    {
        path: 'drug',
        component: DrugComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Drugs'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'drug/:id',
        component: DrugDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Drugs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const drugPopupRoute: Routes = [
    {
        path: 'drug-new',
        component: DrugPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Drugs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'drug/:id/edit',
        component: DrugPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Drugs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'drug/:id/delete',
        component: DrugDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Drugs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
