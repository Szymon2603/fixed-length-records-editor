package com.github.szymon2603.fixedlengthrecordseditor.layout.ui.swing

import com.github.szymon2603.fixedlengthrecordseditor.layout.model.RecordMapping
import javax.swing.JButton
import javax.swing.JComboBox
import javax.swing.JPanel
import javax.swing.JRadioButton
import javax.swing.JTextField

class RecordsLayoutConfigForm {
    lateinit var mainPanel: JPanel
    lateinit var layoutsTablePanel: JPanel
    lateinit var fieldMappingsTablePanel: JPanel
    lateinit var mappingsComboBox: JComboBox<RecordMapping>
    lateinit var addMappingButton: JButton
    lateinit var removeMappingButton: JButton
    lateinit var mappingNameTextField: JTextField
    lateinit var allRecordsRadioButton: JRadioButton
    lateinit var recordsMatchesRadioButton: JRadioButton
    lateinit var recordsMatchesToTextField: JTextField
    lateinit var recordWithIndexRadioButton: JRadioButton
    lateinit var recordIndexTextField: JTextField
}