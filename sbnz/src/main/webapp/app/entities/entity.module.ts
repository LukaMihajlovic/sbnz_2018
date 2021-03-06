import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SbnzDiagnosisModule } from './diagnosis/diagnosis.module';
import { SbnzDoctorModule } from './doctor/doctor.module';
import { SbnzPatientModule } from './patient/patient.module';
import { SbnzAnamnesisModule } from './anamnesis/anamnesis.module';
import { SbnzIngredientModule } from './ingredient/ingredient.module';
import { SbnzDrugModule } from './drug/drug.module';
import { SbnzSymptomModule } from './symptom/symptom.module';
import {SbnzDiagnosingModule} from "./diagnosing/diagnosing.module";
import { SbnzUiawqModule } from './uiawq/uiawq.module';
import { SbnzDiseaseModule } from './disease/disease.module';
import {SbnzReportsModule} from "./reports/reports.module";
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        SbnzDiagnosisModule,
        SbnzDoctorModule,
        SbnzPatientModule,
        SbnzAnamnesisModule,
        SbnzIngredientModule,
        SbnzDrugModule,
        SbnzSymptomModule,
        SbnzDiagnosingModule,
        SbnzReportsModule,
        SbnzUiawqModule,
        SbnzDiseaseModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SbnzEntityModule {}
