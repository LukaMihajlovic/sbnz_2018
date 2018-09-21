import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import {reportsRoute} from "./reports.route";
import {SbnzSharedModule} from "../../shared/shared.module";
import {ReportsComponent} from "./reports.component";
import {RouterModule} from "@angular/router";
import {ReportsService} from "./reports.service";


const ENTITY_STATES = [...reportsRoute];

@NgModule({
    imports: [SbnzSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ReportsComponent],
    entryComponents: [ReportsComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    providers: [
        ReportsService
    ]
})
export class SbnzReportsModule {}
