/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SbnzTestModule } from '../../../test.module';
import { SymptomDialogComponent } from '../../../../../../main/webapp/app/entities/symptom/symptom-dialog.component';
import { SymptomService } from '../../../../../../main/webapp/app/entities/symptom/symptom.service';
import { Symptom } from '../../../../../../main/webapp/app/entities/symptom/symptom.model';
import { DiagnosisService } from '../../../../../../main/webapp/app/entities/diagnosis';

describe('Component Tests', () => {

    describe('Symptom Management Dialog Component', () => {
        let comp: SymptomDialogComponent;
        let fixture: ComponentFixture<SymptomDialogComponent>;
        let service: SymptomService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SbnzTestModule],
                declarations: [SymptomDialogComponent],
                providers: [
                    DiagnosisService,
                    SymptomService
                ]
            })
            .overrideTemplate(SymptomDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SymptomDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SymptomService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Symptom(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.symptom = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'symptomListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Symptom();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.symptom = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'symptomListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
