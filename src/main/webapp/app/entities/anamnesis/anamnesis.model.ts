import { BaseEntity } from './../../shared';

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
