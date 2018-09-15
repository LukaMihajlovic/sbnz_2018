import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DiagnosisComponent } from './diagnosis.component';
import { DiagnosisDetailComponent } from './diagnosis-detail.component';
import { DiagnosisPopupComponent } from './diagnosis-dialog.component';
import { DiagnosisDeletePopupComponent } from './diagnosis-delete-dialog.component';

export const diagnosisRoute: Routes = [
    {
        path: 'diagnosis',
        component: DiagnosisComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Diagnoses'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'diagnosis/:id',
        component: DiagnosisDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Diagnoses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const diagnosisPopupRoute: Routes = [
    {
        path: 'diagnosis-new',
        component: DiagnosisPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Diagnoses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'diagnosis/:id/edit',
        component: DiagnosisPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Diagnoses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'diagnosis/:id/delete',
        component: DiagnosisDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Diagnoses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
