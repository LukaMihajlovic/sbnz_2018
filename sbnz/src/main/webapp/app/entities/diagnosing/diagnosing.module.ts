import { SbnzSharedModule } from '../../shared';
import { DiagnosingComponent } from './diagnosing.component';
import { diagnosingRoute } from './diagnosing.route';
import { RouterModule } from '@angular/router';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';



const ENTITY_STATES = [...diagnosingRoute];

@NgModule({
    imports: [SbnzSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [DiagnosingComponent],
    entryComponents: [DiagnosingComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SbnzDiagnosingModule {}
