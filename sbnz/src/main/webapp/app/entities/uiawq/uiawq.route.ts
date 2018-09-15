import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { UiawqComponent } from './uiawq.component';
import { UiawqDetailComponent } from './uiawq-detail.component';
import { UiawqPopupComponent } from './uiawq-dialog.component';
import { UiawqDeletePopupComponent } from './uiawq-delete-dialog.component';

export const uiawqRoute: Routes = [
    {
        path: 'uiawq',
        component: UiawqComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Uiawqs'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'uiawq/:id',
        component: UiawqDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Uiawqs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const uiawqPopupRoute: Routes = [
    {
        path: 'uiawq-new',
        component: UiawqPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Uiawqs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'uiawq/:id/edit',
        component: UiawqPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Uiawqs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'uiawq/:id/delete',
        component: UiawqDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Uiawqs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
