package com.smartgxt.shared.data;

import java.util.Map;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.smartgxt.shared.encryption.Crypter;

/**
 * Encrypted ModelData, able to encrypt data for transfer.
 * 
 * @author Anton Alexeyev
 * 
 */
@SuppressWarnings("serial")
public class EncryptedModelData extends BaseModelData {

	private boolean isEncrypted = false;

	public EncryptedModelData() {
	}

	public EncryptedModelData(Map<String, Object> properties) {
		super(properties);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <X> X get(String property) {
		X value = super.<X> get(property);
		if (isEncrypted() && (value instanceof String)) {
			String val = (String) Crypter.get().decrypt(value.toString());
			return (X) val;
		}
		return value;
	}

	@SuppressWarnings("unchecked")
	public <X extends Object> X set(String property, X value) {
		if (isEncrypted() && (value instanceof String)) {
			String val = (String) Crypter.get().encrypt(value.toString());
			return (X) super.set(property, val);
		}
		return super.set(property, value);
	};

	/**
	 * Set true to encrypt Strings in this model.
	 */
	public void setEncrypted(boolean isEncrypted) {
		this.isEncrypted = isEncrypted;
	}

	/**
	 * Returns should Strings in this model be encrypted.
	 * 
	 * @return boolean
	 */
	public boolean isEncrypted() {
		return isEncrypted;
	}

}
