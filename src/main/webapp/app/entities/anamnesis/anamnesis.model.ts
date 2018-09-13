
import {BaseEntity} from "../../shared/model/base-entity";

export class Anamnesis implements BaseEntity {
    constructor(
        public id?: number,
        public diagnoses?: BaseEntity[],
        public currentDiagnosis?: BaseEntity,
        public allergiesIngredients?: BaseEntity[],
        public allergiesDrugs?: BaseEntity[],
    ) {
    }
}
