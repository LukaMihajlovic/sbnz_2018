import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DiseaseComponent } from './disease.component';
import { DiseaseDetailComponent } from './disease-detail.component';
import { DiseasePopupComponent } from './disease-dialog.component';
import { DiseaseDeletePopupComponent } from './disease-delete-dialog.component';

export const diseaseRoute: Routes = [
    {
        path: 'disease',
        component: DiseaseComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Diseases'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'disease/:id',
        component: DiseaseDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Diseases'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const diseasePopupRoute: Routes = [
    {
        path: 'disease-new',
        component: DiseasePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Diseases'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'disease/:id/edit',
        component: DiseasePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Diseases'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'disease/:id/delete',
        component: DiseaseDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Diseases'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
