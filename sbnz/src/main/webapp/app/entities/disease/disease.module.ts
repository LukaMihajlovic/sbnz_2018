import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SbnzSharedModule } from '../../shared';
import {
    DiseaseService,
    DiseasePopupService,
    DiseaseComponent,
    DiseaseDetailComponent,
    DiseaseDialogComponent,
    DiseasePopupComponent,
    DiseaseDeletePopupComponent,
    DiseaseDeleteDialogComponent,
    diseaseRoute,
    diseasePopupRoute,
} from './';

const ENTITY_STATES = [
    ...diseaseRoute,
    ...diseasePopupRoute,
];

@NgModule({
    imports: [
        SbnzSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DiseaseComponent,
        DiseaseDetailComponent,
        DiseaseDialogComponent,
        DiseaseDeleteDialogComponent,
        DiseasePopupComponent,
        DiseaseDeletePopupComponent,
    ],
    entryComponents: [
        DiseaseComponent,
        DiseaseDialogComponent,
        DiseasePopupComponent,
        DiseaseDeleteDialogComponent,
        DiseaseDeletePopupComponent,
    ],
    providers: [
        DiseaseService,
        DiseasePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SbnzDiseaseModule {}
