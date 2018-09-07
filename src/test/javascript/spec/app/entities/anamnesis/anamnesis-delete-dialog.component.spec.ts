/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SbnzTestModule } from '../../../test.module';
import { AnamnesisDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/anamnesis/anamnesis-delete-dialog.component';
import { AnamnesisService } from '../../../../../../main/webapp/app/entities/anamnesis/anamnesis.service';

describe('Component Tests', () => {

    describe('Anamnesis Management Delete Component', () => {
        let comp: AnamnesisDeleteDialogComponent;
        let fixture: ComponentFixture<AnamnesisDeleteDialogComponent>;
        let service: AnamnesisService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SbnzTestModule],
                declarations: [AnamnesisDeleteDialogComponent],
                providers: [
                    AnamnesisService
                ]
            })
            .overrideTemplate(AnamnesisDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AnamnesisDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnamnesisService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
