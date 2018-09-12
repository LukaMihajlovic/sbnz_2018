import { BaseEntity } from './../../shared';

export class Disease implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public factor?: number,
        public symptoms?: BaseEntity[],
        public diagnoses?: BaseEntity[],
    ) {
    }
}
