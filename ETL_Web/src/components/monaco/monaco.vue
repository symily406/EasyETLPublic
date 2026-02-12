<template>
	<div ref="codeEditor" style="width: 100%; height: 100%; text-align: left;"></div>
</template>

<script>
	import * as monaco from "monaco-editor";
	export default {
		name: "codeEditor",
		data() {
			return {
				monacoEditor: null,
			};
		},
		props: {
			language: {
				type: String,
				default: 'plaintext'
			},
			value: {
				type: String,
				default: ''
			},
			readOnly: {
				type: Boolean,
				default: false
			},
			fontSize: {
				type: Number,
				default: 14
			}
		},
		created() {

		},
		watch: {
			value(newCode) {
				this.monacoEditor.setValue(newCode);
			}
		},
		mounted() {
			this.$nextTick(() => {
				this.initMonaco();
			})
		},
		methods: {
			initMonaco() {
				monaco.languages.registerCompletionItemProvider('sql', {
					provideCompletionItems: function(model, position) {
						// 获取当前行数
						const line = position.lineNumber;

						// 获取当前列数
						const column = position.column;
						// 获取当前输入行的所有内容
						const content = model.getLineContent(line)
						// 通过下标来获取当前光标后一个内容，即为刚输入的内容
						const sym = content[column - 2];
						var textUntilPosition = model.getValueInRange({
							startLineNumber: 1,
							startColumn: 1,
							endLineNumber: position.lineNumber,
							endColumn: position.column
						});
						var word = model.getWordUntilPosition(position);
						var range = {
							startLineNumber: position.lineNumber,
							endLineNumber: position.lineNumber,
							startColumn: word.startColumn,
							endColumn: word.endColumn
						};
						//---------------------------------------------------
						//上面的代码仅仅是为了获取sym，即提示符
						//---------------------------------------------------
						var suggestions = [];
						if (sym === "$") {
							//...
							//拦截到用户输入$，开始设置提示内容，同else中代码一致，自行拓展
						} else {
							//直接提示，以下为sql语句关键词提示
							var sqlStr = [
								'select', 'from', 'where', 'and', 'or', 'in', 'like',
								'insert into',
								'update', 'delete from', 'group by', 'order by', 'limit',
								'offset', 'as',
								'join', 'inner join', 'left join', 'right join', 'count',
								'sum', 'avg',
								'max', 'min', 'distinct', 'now()', 'curdate()', 'curtime()',
								'date_add()',
								'datediff()', 'ifnull()', 'coalesce()', 'concat()', 'lower()',
								'upper()',
								'explode()', 'collect_list()', 'groupBy()', 'orderBy()',
								'alias()',
								'first_value (expression) over([partition by column1,...]order by column3[asc|desc]...)',
								'last_value (expression) over([partition by column1,...]order by column3[asc|desc]...)',
								'alter table ', 'truncate table ', 'current_timestamp',
								'current_date',
								'length',
								'substr(string str, int start, int length)',
								'case  when then end', 'nvl',
								'date_format(current_timestamp,\'yyyy-MM-dd HH:mm:ss\') as '
							];
							for (var i in sqlStr) {
								suggestions.push({
									label: sqlStr[i], // 显示的提示内容
									kind: monaco.languages.CompletionItemKind[
										'Function'], // 用来显示提示内容后的不同的图标
									insertText: sqlStr[i], // 选择后粘贴到编辑器中的文字
									detail: '', // 提示内容后的说明
									range: range
								});
							}
						}
						return {
							suggestions: suggestions
						};
					},
					triggerCharacters: ["$", ""]
				});
				this.monacoEditor = monaco.editor.create(this.$refs.codeEditor, {
					theme: "vs", // 主题
					value: this.value, // 默认显示的值
					language: this.language,
					folding: true, // 是否折叠
					foldingHighlight: true, // 折叠等高线
					foldingStrategy: "auto", // 折叠方式
					showFoldingControls: "always", // 是否一直显示折叠
					disableLayerHinting: true, // 等宽优化
					emptySelectionClipboard: false, // 空选择剪切板
					selectionClipboard: false, // 选择剪切板
					automaticLayout: true, // 自动布局
					codeLens: true, // 代码镜头
					scrollBeyondLastLine: false, // 滚动完最后一行后再滚动一屏幕
					colorDecorators: true, // 颜色装饰器
					accessibilitySupport: "on", // 辅助功能支持"auto" | "off" | "on"
					lineNumbers: "on", // 行号 取值： "on" | "off" | "relative" | "interval" | function
					lineNumbersMinChars: 4, // 行号最小字符   number
					enableSplitViewResizing: false,
					readOnly: this.readOnly, //是否只读  取值 true | false
					fontSize: this.fontSize
				});
				this.monacoEditor.onDidChangeModelContent(() => {
					this.$emit('change', this.monacoEditor.getValue());
				});
			},
			getSelectCode() {
				const selection = this.monacoEditor.getSelection() // 获取光标选中的值
				const {
					startLineNumber,
					endLineNumber,
					startColumn,
					endColumn
				} = selection
				const model = this.monacoEditor.getModel()

				return model.getValueInRange({
					startLineNumber,
					startColumn,
					endLineNumber,
					endColumn,
				});
			}
		}
	};
</script>

<style>
</style>