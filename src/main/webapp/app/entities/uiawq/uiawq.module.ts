import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SbnzSharedModule } from '../../shared';
import {
    UiawqService,
    UiawqPopupService,
    UiawqComponent,
    UiawqDetailComponent,
    UiawqDialogComponent,
    UiawqPopupComponent,
    UiawqDeletePopupComponent,
    UiawqDeleteDialogComponent,
    uiawqRoute,
    uiawqPopupRoute,
} from './';

const ENTITY_STATES = [
    ...uiawqRoute,
    ...uiawqPopupRoute,
];

@NgModule({
    imports: [
        SbnzSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        UiawqComponent,
        UiawqDetailComponent,
        UiawqDialogComponent,
        UiawqDeleteDialogComponent,
        UiawqPopupComponent,
        UiawqDeletePopupComponent,
    ],
    entryComponents: [
        UiawqComponent,
        UiawqDialogComponent,
        UiawqPopupComponent,
        UiawqDeleteDialogComponent,
        UiawqDeletePopupComponent,
    ],
    providers: [
        UiawqService,
        UiawqPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SbnzUiawqModule {}
