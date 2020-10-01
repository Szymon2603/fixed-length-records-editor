package com.github.szymon2603.fixedlengthrecordseditor.mappings.ui

import com.github.szymon2603.fixedlengthrecordseditor.mappings.model.RecordFieldDescriptor
import com.github.szymon2603.fixedlengthrecordseditor.mappings.model.RecordFieldsMapping
import com.github.szymon2603.fixedlengthrecordseditor.mappings.model.RecordValueAlignment
import com.github.szymon2603.fixedlengthrecordseditor.recordseditor.ui.Column
import com.github.szymon2603.fixedlengthrecordseditor.recordseditor.ui.ListTableModel
import java.util.UUID
import javax.swing.DefaultCellEditor
import javax.swing.JComboBox
import javax.swing.table.TableCellEditor
import javax.swing.table.TableModel

class RecordFieldsMappingsFormModel(
    initialModel: List<Pair<UUID, RecordFieldsMapping>>,
    var uuidGenerator: () -> UUID = UUID::randomUUID
) {
    private val mappings = initialModel.map { MappingModel(it) }.toMutableList()
    val currentFormState: Map<UUID, RecordFieldsMapping>
        get() = mappings.map { it.toDomainModel() }.toMap()

    private val _mappingsTableModel = ListTableModel(mappings, listOf(MappingNameColumn))
    val mappingsTableModel: TableModel
        get() = _mappingsTableModel

    private val _descriptorsTableModel = createDescriptorsTableModel()
    val descriptorsTableModel: TableModel
        get() = _descriptorsTableModel

    private fun createDescriptorsTableModel(): ListTableModel<RecordFieldDescriptorModel> {
        val fieldDescriptors = if (mappings.isNotEmpty()) {
            mappings[0].mapping.fieldDescriptors
        } else {
            emptyList<RecordFieldDescriptorModel>().toMutableList()
        }
        val columns = listOf(DescriptorNameColumn, StartIndexColumn, EndIndexColumn, LengthColumn, AlignmentColumn)
        return ListTableModel(fieldDescriptors, columns)
    }

    fun addMapping(selectedMapping: Int) {
        val key = uuidGenerator()
        val mapping = RecordFieldsMappingModel("New mapping")
        _mappingsTableModel.addRow(MappingModel(key, mapping), selectedMapping + 1)
    }

    fun removeMapping(selectedMapping: Int) {
        _mappingsTableModel.removeRow(selectedMapping)
    }

    fun addDescriptor(selectedDescriptor: Int) {
        val descriptor = RecordFieldDescriptorModel("New column", 0, 1, RecordValueAlignment.LEFT)
        _descriptorsTableModel.addRow(descriptor, selectedDescriptor + 1)
    }

    fun removeDescriptor(selectedDescriptor: Int) {
        _descriptorsTableModel.removeRow(selectedDescriptor)
    }

    fun updateDescriptorsModelFor(selectedMapping: Int) {
        val fieldDescriptors = if (selectedMapping != -1) {
            mappings[selectedMapping].mapping.fieldDescriptors
        } else {
            emptyList<RecordFieldDescriptorModel>().toMutableList()
        }
        _descriptorsTableModel.update(fieldDescriptors)
    }

    fun isDescriptorListEmpty(selectedMapping: Int): Boolean {
        return if (selectedMapping == -1) true else mappings[selectedMapping].mapping.fieldDescriptors.isEmpty()
    }
}

private data class MappingModel(val key: UUID, val mapping: RecordFieldsMappingModel) {
    constructor(domainModel: Pair<UUID, RecordFieldsMapping>) : this(
        domainModel.first,
        RecordFieldsMappingModel(domainModel.second)
    )

    fun toDomainModel(): Pair<UUID, RecordFieldsMapping> {
        return key to mapping.toDomainModel()
    }
}

private data class RecordFieldsMappingModel(
    var name: String,
    val fieldDescriptors: MutableList<RecordFieldDescriptorModel> = mutableListOf()
) {
    constructor(domainModel: RecordFieldsMapping) : this(
        domainModel.name,
        domainModel.fieldDescriptors.map { RecordFieldDescriptorModel(it) }.toMutableList()
    )

    fun toDomainModel(): RecordFieldsMapping {
        return RecordFieldsMapping(name, fieldDescriptors.map { it.toDomainModel() })
    }
}

private data class RecordFieldDescriptorModel(
    var name: String,
    var startIndex: Int,
    var endIndex: Int,
    var alignment: RecordValueAlignment
) {
    val length: Int
        get() = endIndex - startIndex

    constructor(
        domainModel: RecordFieldDescriptor
    ) : this(domainModel.name, domainModel.startIndex, domainModel.endIndex, domainModel.alignment)

    fun toDomainModel(): RecordFieldDescriptor {
        return RecordFieldDescriptor(name, startIndex, endIndex, alignment)
    }
}

private object MappingNameColumn : Column<MappingModel> {
    override fun getCellValue(row: MappingModel): Any = row.mapping.name

    override fun setCellValue(row: MappingModel, newValue: Any) {
        row.mapping.name = newValue as String
    }

    override fun isEditable(): Boolean = true

    override fun getColumnClass(): Class<*> = String::class.java

    override fun getName(): String = "Mapping name"
}

private object DescriptorNameColumn : Column<RecordFieldDescriptorModel> {
    override fun getCellValue(row: RecordFieldDescriptorModel): Any = row.name

    override fun setCellValue(row: RecordFieldDescriptorModel, newValue: Any) {
        row.name = newValue as String
    }

    override fun isEditable(): Boolean = true

    override fun getColumnClass(): Class<*> = String::class.java

    override fun getName(): String = "Column name"
}

private object StartIndexColumn : Column<RecordFieldDescriptorModel> {
    override fun getCellValue(row: RecordFieldDescriptorModel): Any = row.startIndex

    override fun setCellValue(row: RecordFieldDescriptorModel, newValue: Any) {
        row.startIndex = newValue as Int
    }

    override fun isEditable(): Boolean = true

    override fun getColumnClass(): Class<*> = Integer::class.java

    override fun getName(): String = "Start index"
}

private object EndIndexColumn : Column<RecordFieldDescriptorModel> {
    override fun getCellValue(row: RecordFieldDescriptorModel): Any = row.endIndex

    override fun setCellValue(row: RecordFieldDescriptorModel, newValue: Any) {
        row.endIndex = newValue as Int
    }

    override fun isEditable(): Boolean = true

    override fun getColumnClass(): Class<*> = Integer::class.java

    override fun getName(): String = "End index"
}

private object LengthColumn : Column<RecordFieldDescriptorModel> {
    override fun getCellValue(row: RecordFieldDescriptorModel): Any = row.length

    override fun setCellValue(row: RecordFieldDescriptorModel, newValue: Any) {
        row.endIndex = row.startIndex + newValue as Int
    }

    override fun isEditable(): Boolean = true

    override fun getColumnClass(): Class<*> = Integer::class.java

    override fun getName(): String = "Length"
}

private object AlignmentColumn : Column<RecordFieldDescriptorModel> {
    override fun getCellValue(row: RecordFieldDescriptorModel): Any = row.alignment

    override fun setCellValue(row: RecordFieldDescriptorModel, newValue: Any) {
        row.alignment = newValue as RecordValueAlignment
    }

    override fun isEditable(): Boolean = true

    override fun getColumnClass(): Class<*> = RecordFieldDescriptorModel::alignment::class.java

    override fun getName(): String = "Alignment"

    override fun getCellEditor(): TableCellEditor = DefaultCellEditor(JComboBox(RecordValueAlignment.values()))
}
