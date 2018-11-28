package io.mosip.kernel.masterdata.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.mosip.kernel.core.dataaccess.exception.DataAccessLayerException;
import io.mosip.kernel.masterdata.constant.ValidDocumentErrorCode;
import io.mosip.kernel.masterdata.dto.ValidDocumentRequestDto;
import io.mosip.kernel.masterdata.entity.ValidDocument;
import io.mosip.kernel.masterdata.entity.ValidDocumentId;
import io.mosip.kernel.masterdata.exception.MasterDataServiceException;
import io.mosip.kernel.masterdata.repository.ValidDocumentRepository;
import io.mosip.kernel.masterdata.service.ValidDocumentService;
import io.mosip.kernel.masterdata.utils.MetaDataUtils;

/**
 * This service class contains methods that create valid document in table.
 * 
 * @author Ritesh Sinha
 * @since 1.0.0
 *
 */
@Service
public class ValidDocumentServiceImpl implements ValidDocumentService {

	/**
	 * Reference to ValidDocumentRepository.
	 */
	@Autowired
	private ValidDocumentRepository documentRepository;

	/**
	 * Reference to MetaDataUtils
	 */
	@Autowired
	private MetaDataUtils metaUtils;

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.mosip.kernel.masterdata.service.ValidDocumentService#
	 * insertDocumentCategory(io.mosip.kernel.masterdata.dto.
	 * ValidDocumentRequestDto)
	 */
	@Override
	public ValidDocumentId insertDocumentCategory(ValidDocumentRequestDto document) {

		ValidDocument validDocument = metaUtils.setCreateMetaData(document.getRequest().getValidDocument(),
				ValidDocument.class);
		try {
			validDocument = documentRepository.create(validDocument);
		} catch (DataAccessLayerException e) {
			throw new MasterDataServiceException(ValidDocumentErrorCode.VALID_DOCUMENT_INSERT_EXCEPTION.getErrorCode(),
					e.getErrorText());
		}

		ValidDocumentId validDocumentId = new ValidDocumentId();
		validDocumentId.setDocCategoryCode(validDocument.getDocCategoryCode());
		validDocumentId.setDocTypeCode(validDocument.getDocTypeCode());
		return validDocumentId;
	}

}
