
import {ISymptom} from "./symptom.model";

export interface IDiagnosing {
    symptoms?: ISymptom[];
    anamnesisId?: number;
    diseases?: any;
}
