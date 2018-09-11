import { BaseEntity } from './../../shared';

export class Symptom implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public low?: number,
        public high?: number,
        public spec?: boolean,
        public diagnoses?: BaseEntity[],
    ) {
        this.spec = false;
    }
}
