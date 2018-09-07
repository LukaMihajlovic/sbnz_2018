import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { AnamnesisComponent } from './anamnesis.component';
import { AnamnesisDetailComponent } from './anamnesis-detail.component';
import { AnamnesisPopupComponent } from './anamnesis-dialog.component';
import { AnamnesisDeletePopupComponent } from './anamnesis-delete-dialog.component';

export const anamnesisRoute: Routes = [
    {
        path: 'anamnesis',
        component: AnamnesisComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Anamneses'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'anamnesis/:id',
        component: AnamnesisDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Anamneses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const anamnesisPopupRoute: Routes = [
    {
        path: 'anamnesis-new',
        component: AnamnesisPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Anamneses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'anamnesis/:id/edit',
        component: AnamnesisPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Anamneses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'anamnesis/:id/delete',
        component: AnamnesisDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Anamneses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
