import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SbnzSharedModule } from '../../shared';
import {
    PatientService,
    PatientPopupService,
    PatientComponent,
    PatientDetailComponent,
    PatientDialogComponent,
    PatientPopupComponent,
    PatientDeletePopupComponent,
    PatientDeleteDialogComponent,
    patientRoute,
    patientPopupRoute,
} from './';

const ENTITY_STATES = [
    ...patientRoute,
    ...patientPopupRoute,
];

@NgModule({
    imports: [
        SbnzSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PatientComponent,
        PatientDetailComponent,
        PatientDialogComponent,
        PatientDeleteDialogComponent,
        PatientPopupComponent,
        PatientDeletePopupComponent,
    ],
    entryComponents: [
        PatientComponent,
        PatientDialogComponent,
        PatientPopupComponent,
        PatientDeleteDialogComponent,
        PatientDeletePopupComponent,
    ],
    providers: [
        PatientService,
        PatientPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SbnzPatientModule {}
