import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SbnzSharedModule } from '../../shared';
import {
    SymptomService,
    SymptomPopupService,
    SymptomComponent,
    SymptomDetailComponent,
    SymptomDialogComponent,
    SymptomPopupComponent,
    SymptomDeletePopupComponent,
    SymptomDeleteDialogComponent,
    symptomRoute,
    symptomPopupRoute,
} from './';

const ENTITY_STATES = [
    ...symptomRoute,
    ...symptomPopupRoute,
];

@NgModule({
    imports: [
        SbnzSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SymptomComponent,
        SymptomDetailComponent,
        SymptomDialogComponent,
        SymptomDeleteDialogComponent,
        SymptomPopupComponent,
        SymptomDeletePopupComponent,
    ],
    entryComponents: [
        SymptomComponent,
        SymptomDialogComponent,
        SymptomPopupComponent,
        SymptomDeleteDialogComponent,
        SymptomDeletePopupComponent,
    ],
    providers: [
        SymptomService,
        SymptomPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SbnzSymptomModule {}
