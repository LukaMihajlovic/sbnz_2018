import { Routes } from '@angular/router';
import { UserRouteAccessService } from '../../shared';
import {DiagnosingComponent} from './diagnosing.component';

export const diagnosingRoute: Routes = [
    {
        path: 'diagnosing',
        component: DiagnosingComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Diagnoisng'
        },
        canActivate: [UserRouteAccessService]
    }
];
