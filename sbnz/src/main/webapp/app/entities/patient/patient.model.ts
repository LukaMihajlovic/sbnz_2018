import { BaseEntity } from './../../shared';

export class Patient implements BaseEntity {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public anamnesis?: BaseEntity,
    ) {
    }
}
