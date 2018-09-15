import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SbnzSharedModule } from '../../shared';
import { SbnzAdminModule } from '../../admin/admin.module';
import {
    DoctorService,
    DoctorPopupService,
    DoctorComponent,
    DoctorDetailComponent,
    DoctorDialogComponent,
    DoctorPopupComponent,
    DoctorDeletePopupComponent,
    DoctorDeleteDialogComponent,
    doctorRoute,
    doctorPopupRoute,
} from './';

const ENTITY_STATES = [
    ...doctorRoute,
    ...doctorPopupRoute,
];

@NgModule({
    imports: [
        SbnzSharedModule,
        SbnzAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DoctorComponent,
        DoctorDetailComponent,
        DoctorDialogComponent,
        DoctorDeleteDialogComponent,
        DoctorPopupComponent,
        DoctorDeletePopupComponent,
    ],
    entryComponents: [
        DoctorComponent,
        DoctorDialogComponent,
        DoctorPopupComponent,
        DoctorDeleteDialogComponent,
        DoctorDeletePopupComponent,
    ],
    providers: [
        DoctorService,
        DoctorPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SbnzDoctorModule {}
