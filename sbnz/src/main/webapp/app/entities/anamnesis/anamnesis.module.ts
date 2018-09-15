import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SbnzSharedModule } from '../../shared';
import {
    AnamnesisService,
    AnamnesisPopupService,
    AnamnesisComponent,
    AnamnesisDetailComponent,
    AnamnesisDialogComponent,
    AnamnesisPopupComponent,
    AnamnesisDeletePopupComponent,
    AnamnesisDeleteDialogComponent,
    anamnesisRoute,
    anamnesisPopupRoute,
} from './';

const ENTITY_STATES = [
    ...anamnesisRoute,
    ...anamnesisPopupRoute,
];

@NgModule({
    imports: [
        SbnzSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AnamnesisComponent,
        AnamnesisDetailComponent,
        AnamnesisDialogComponent,
        AnamnesisDeleteDialogComponent,
        AnamnesisPopupComponent,
        AnamnesisDeletePopupComponent,
    ],
    entryComponents: [
        AnamnesisComponent,
        AnamnesisDialogComponent,
        AnamnesisPopupComponent,
        AnamnesisDeleteDialogComponent,
        AnamnesisDeletePopupComponent,
    ],
    providers: [
        AnamnesisService,
        AnamnesisPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SbnzAnamnesisModule {}
