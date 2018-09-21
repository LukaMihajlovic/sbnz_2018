import { Routes } from '@angular/router';
import {UserRouteAccessService} from "../../shared/auth/user-route-access-service";
import {ReportsComponent} from "./reports.component";

export const reportsRoute: Routes = [
    {
        path: 'reports',
        component: ReportsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Reports'
        },
        canActivate: [UserRouteAccessService]
    }
];
